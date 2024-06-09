package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.WardAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TangletroveKelp extends CardImpl {

    public TangletroveKelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.CLUE, SubType.PLANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2), false));

        // At the beginning of each combat, other Clues you control become 6/6 Plant creatures in addition to their other types until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new TangletroveKelpEffect(), TargetController.ANY, false));

        // {2}, Sacrifice Tangletrove Kelp: Draw a card.
        this.addAbility(new ClueAbility(true));
    }

    private TangletroveKelp(final TangletroveKelp card) {
        super(card);
    }

    @Override
    public TangletroveKelp copy() {
        return new TangletroveKelp(this);
    }
}

class TangletroveKelpEffect extends ContinuousEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other Clues you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.CLUE.getPredicate());
    }

    TangletroveKelpEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "other Clues you control become 6/6 Plant creatures in addition to their other types until end of turn";
    }

    private TangletroveKelpEffect(final TangletroveKelpEffect effect) {
        super(effect);
    }

    @Override
    public TangletroveKelpEffect copy() {
        return new TangletroveKelpEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.PTChangingEffects_7;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent clue : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    clue.addCardType(game, CardType.CREATURE);
                    clue.addSubType(game, SubType.PLANT);
                    break;
                case PTChangingEffects_7:
                    clue.getToughness().setModifiedBaseValue(6);
                    clue.getPower().setModifiedBaseValue(6);
            }
        }
        return true;
    }
}
