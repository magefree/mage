package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;

/**
 * @author TheElk801
 */
public final class KykarWindsFury extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SPIRIT, "a Spirit");

    public KykarWindsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SpiritWhiteToken()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Sacrifice a Spirit: Add {R}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filter)),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ));
    }

    private KykarWindsFury(final KykarWindsFury card) {
        super(card);
    }

    @Override
    public KykarWindsFury copy() {
        return new KykarWindsFury(this);
    }
}
