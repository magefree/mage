package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AgadeemsAwakening extends CardImpl {

    public AgadeemsAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.a.AgadeemTheUndercrypt.class;

        // Return from your graveyard to the battlefield any number of target creature cards that each have a different converted mana cost X or less.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText(
                "return from your graveyard to the battlefield any number of target creature cards " +
                        "that each have a different converted mana cost X or less"
        ));
        this.getSpellAbility().setTargetAdjuster(AgadeemsAwakeningAdjuster.instance);
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
            = new FilterCreatureCard("creature cards that each have a different converted mana cost X or less");
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
    public Set<UUID> possibleTargets(UUID sourceId, UUID playerId, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, playerId, game);
        Set<Integer> cmcs = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getConvertedManaCost)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && (cmcs.contains(card.getConvertedManaCost()) || card.getConvertedManaCost() > xValue);
        });
        return possibleTargets;
    }
}
