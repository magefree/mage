package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HexproofFromBlackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GarruksHarbinger extends CardImpl {

    public GarruksHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Hexproof from Black
        this.addAbility(HexproofFromBlackAbility.getInstance());

        // Whenever Garruk's Harbinger deals combat damage to a player or planeswalker, look at that many cards from the top of your library. You may reveal a creature card or Garruk planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new GarruksHarbingerEffect(), false, true)
                        .setOrPlaneswalker(true)
        );
    }

    private GarruksHarbinger(final GarruksHarbinger card) {
        super(card);
    }

    @Override
    public GarruksHarbinger copy() {
        return new GarruksHarbinger(this);
    }
}

class GarruksHarbingerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a creature or Garruk planeswalker card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), Predicates.and(CardType.PLANESWALKER.getPredicate(), SubType.GARRUK.getPredicate())));
    }

    GarruksHarbingerEffect() {
        super(Outcome.Benefit);
        staticText = "look at that many cards from the top of your library. You may reveal a creature card or Garruk planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in a random order";
    }

    private GarruksHarbingerEffect(GarruksHarbingerEffect effect) {
        super(effect);
    }

    @Override
    public GarruksHarbingerEffect copy() {
        return new GarruksHarbingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) getValue("damage");
        if (damage != null) {
            LookLibraryAndPickControllerEffect effect = new LookLibraryAndPickControllerEffect(StaticValue.get(damage), false, StaticValue.get(1), filter, false);
            effect.setBackInRandomOrder(true);
            return effect.apply(game, source);
        }
        return false;
    }
}