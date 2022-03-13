package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public final class AgadeemsAwakening extends ModalDoubleFacesCard {

    public AgadeemsAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{X}{B}{B}{B}",
                "Agadeem, the Undercrypt", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Agadeem's Awakening
        // Sorcery

        // Return from your graveyard to the battlefield any number of target creature cards that each have a different converted mana cost X or less.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText(
                "return from your graveyard to the battlefield any number of target creature cards " +
                        "that each have a different mana value X or less"
        ));
        this.getLeftHalfCard().getSpellAbility().setTargetAdjuster(AgadeemsAwakeningAdjuster.instance);

        // 2.
        // Agadeem, the Undercrypt
        // Land

        // As Agadeem, the Undercrypt enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private AgadeemsAwakening(final AgadeemsAwakening card) {
        super(card);
    }

    @Override
    public AgadeemsAwakening copy() {
        return new AgadeemsAwakening(this);
    }
}

enum AgadeemsAwakeningAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new AgadeemsAwakeningTarget(ability.getManaCostsToPay().getX()));
    }
}

class AgadeemsAwakeningTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards that each have a different mana value X or less");
    private final int xValue;

    AgadeemsAwakeningTarget(int xValue) {
        super(0, Integer.MAX_VALUE, filter, true);
        this.xValue = xValue;
    }

    private AgadeemsAwakeningTarget(final AgadeemsAwakeningTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public AgadeemsAwakeningTarget copy() {
        return new AgadeemsAwakeningTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<Integer> cmcs = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getManaValue)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && (cmcs.contains(card.getManaValue()) || card.getManaValue() > xValue);
        });
        return possibleTargets;
    }
}
