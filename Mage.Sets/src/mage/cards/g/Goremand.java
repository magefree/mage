package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Goremand extends CardImpl {

    public Goremand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Goremand enters the battlefield, each opponent sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE)
        ));
    }

    private Goremand(final Goremand card) {
        super(card);
    }

    @Override
    public Goremand copy() {
        return new Goremand(this);
    }
}
