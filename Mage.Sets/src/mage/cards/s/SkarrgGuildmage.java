
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SkarrgGuildmage extends CardImpl {

    public SkarrgGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}{G}: Creatures you control gain trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), "Creatures you control gain trample until end of turn"),
                new ManaCostsImpl<>("{R}{G}")));
        // {1}{R}{G}: Target land you control becomes a 4/4 Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4, "4/4 Elemental creature", SubType.ELEMENTAL),
                false, true, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}{G}") );
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);

    }

    private SkarrgGuildmage(final SkarrgGuildmage card) {
        super(card);
    }

    @Override
    public SkarrgGuildmage copy() {
        return new SkarrgGuildmage(this);
    }
}