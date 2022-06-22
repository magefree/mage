package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SpitFlame extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public SpitFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Spit Flame deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Whenever a Dragon enters the battlefield under your control, you may pay {R}. If you do, return Spit Flame from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToHandEffect(),
                        new ManaCostsImpl<>("{R}")
                ), filter, false
        ));
    }

    private SpitFlame(final SpitFlame card) {
        super(card);
    }

    @Override
    public SpitFlame copy() {
        return new SpitFlame(this);
    }
}
