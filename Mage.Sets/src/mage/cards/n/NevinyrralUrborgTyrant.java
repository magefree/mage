package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.abilities.keyword.HexproofFromArtifactsCreaturesAndEnchantments;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NevinyrralUrborgTyrant extends CardImpl {

    public NevinyrralUrborgTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Hexproof from artifacts, creatures, and enchantments
        this.addAbility(HexproofFromArtifactsCreaturesAndEnchantments.getInstance());

        // When Nevinyrral, Urborg Tyrant enters the battlefield, create a tapped 2/2 black Zombie creature token for each creature that died this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new ZombieToken(), CreaturesDiedThisTurnCount.instance, true, false
        ).setText("create a tapped 2/2 black Zombie creature token for each creature that died this turn")).addHint(CreaturesDiedThisTurnHint.instance));

        // When Nevinyrral dies, you may pay {1}. When you do, destroy all artifacts, creatures, and enchantments.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DoWhenCostPaid(new ReflexiveTriggeredAbility(
                        new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT),
                        false, "destroy all artifacts, creatures, and enchantments"
                ), new GenericManaCost(1), "Pay {1}?")
        ));
    }

    private NevinyrralUrborgTyrant(final NevinyrralUrborgTyrant card) {
        super(card);
    }

    @Override
    public NevinyrralUrborgTyrant copy() {
        return new NevinyrralUrborgTyrant(this);
    }
}
