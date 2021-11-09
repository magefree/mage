package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SnapcasterMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card in your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public SnapcasterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SnapcasterMageEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SnapcasterMage(final SnapcasterMage card) {
        super(card);
    }

    @Override
    public SnapcasterMage copy() {
        return new SnapcasterMage(this);
    }
}

class SnapcasterMageEffect extends ContinuousEffectImpl {

    public SnapcasterMageEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
    }

    public SnapcasterMageEffect(final SnapcasterMageEffect effect) {
        super(effect);
    }

    @Override
    public SnapcasterMageEffect copy() {
        return new SnapcasterMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
            return true;
        }
        return false;
    }
}
