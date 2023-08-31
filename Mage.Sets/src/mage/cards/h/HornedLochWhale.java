package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornedLochWhale extends AdventureCard {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public HornedLochWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{U}{U}", "Lagoon Breach", "{1}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Horned Loch-Whale enters the battlefield tapped unless it's your turn.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new TapSourceEffect(true), NotMyTurnCondition.instance,
                "tapped unless it's your turn"
        )));

        // Lagoon Breach
        // The owner of target attacking creature you don't control puts it on the top or bottom of their library.
        this.getSpellCard().getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        this.finalizeAdventure();
    }

    private HornedLochWhale(final HornedLochWhale card) {
        super(card);
    }

    @Override
    public HornedLochWhale copy() {
        return new HornedLochWhale(this);
    }
}
