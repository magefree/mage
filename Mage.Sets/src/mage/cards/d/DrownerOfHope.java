
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DrownerOfHope extends CardImpl {

    private static final FilterControlledPermanent FILTER = new FilterControlledPermanent("an Eldrazi Scion");

    static {
        FILTER.add(Predicates.and(
                SubType.ELDRAZI.getPredicate(),
                SubType.SCION.getPredicate()));
    }

    public DrownerOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Drowner of Hope enters the battlefield, create two 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken(), 2);
        effect.setText("create two 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

        // Sacrifice an Eldrazi Scion: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new SacrificeTargetCost(new TargetControlledPermanent(FILTER)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DrownerOfHope(final DrownerOfHope card) {
        super(card);
    }

    @Override
    public DrownerOfHope copy() {
        return new DrownerOfHope(this);
    }
}
