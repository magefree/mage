package mage.cards.h;

import java.util.Optional;
import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.hint.StaticHint;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author sobiech
 */
public final class HashatonScarabsFist extends CardImpl {

    public HashatonScarabsFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you discard a creature card, you may pay {2}{U}. If you do, create a tapped token that's a copy of that card, except it's a 4/4 black Zombie.
        final Ability ability = new DiscardCardControllerTriggeredAbility(
                new DoIfCostPaid(new HashatonScarabsFistEffect(), new ManaCostsImpl<>("{2}{U}"))
                        .withChooseHint(new HashatonScarabsFistHint()),
                false, StaticFilters.FILTER_CARD_CREATURE_A
        );
        this.addAbility(ability);
    }

    private HashatonScarabsFist(final HashatonScarabsFist card) {
        super(card);
    }

    @Override
    public HashatonScarabsFist copy() {
        return new HashatonScarabsFist(this);
    }
}

class HashatonScarabsFistHint extends StaticHint {

    public HashatonScarabsFistHint() {
        super("");
    }

    @Override
    public String getText(Game game, Ability source) {
        if (source.getEffects().isEmpty()) {
            return "";
        }
        for (Effect effect : source.getEffects()) {
            Card discardedCard = (Card) effect.getValue("discardedCard");
            if (discardedCard != null) {
                return "Discarded card: " + discardedCard.getName();
            }
        }
        return "";
    }
}

class HashatonScarabsFistEffect extends OneShotEffect {

    HashatonScarabsFistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a tapped token that's a copy of that card, except it's a 4/4 black Zombie";
    }

    private HashatonScarabsFistEffect(OneShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        return Optional.ofNullable(this.values.get("discardedCard"))
                .map(CardImpl.class::cast)
                .filter(CardImpl::isCreature)
                //create a tapped token that's a copy of that card, except it's a 4/4 black Zombie.
                .map(card -> new CreateTokenCopyTargetEffect(null, null, false, 1,
                                true, false, null, 4, 4, false)
                                .setOnlyColor(ObjectColor.BLACK)
                                .setOnlySubType(SubType.ZOMBIE)
                                .setTargetPointer(new FixedTarget(card, game))
                                .apply(game, source)
                )
                .orElse(false);
    }

    @Override
    public HashatonScarabsFistEffect copy() {
        return new HashatonScarabsFistEffect(this);
    }
}
