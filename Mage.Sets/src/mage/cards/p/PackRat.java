
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * The token will copy Pack Rat's two abilities. Its power and toughness will be
 * equal to the number of Rats you control (not the number of Rats you
 * controlled when the token entered the battlefield). It will also be able to
 * create copies of itself.
 *
 * The token won't copy counters on Pack Rat, nor will it copy other effects
 * that have changed Pack Rat's power, toughness, types, color, or so on.
 * Normally, this means the token will simply be a Pack Rat. But if any copy
 * effects have affected that Pack Rat, they're taken into account.
 *
 * If Pack Rat leaves the battlefield before its activated ability resolves, the
 * token will still enter the battlefield as a copy of Pack Rat, using Pack
 * Rat's copiable values from when it was last on the battlefield.
 *
 *
 * @author LevelX2
 */
public final class PackRat extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Rats you control");

    static {
        filter.add(SubType.RAT.getPredicate());
    }

    public PackRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Pack Rat's power and toughness are each equal to the number of Rats you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        // {2}{B}, Discard a card: Create a token that's a copy of Pack Rat.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenCopySourceEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private PackRat(final PackRat card) {
        super(card);
    }

    @Override
    public PackRat copy() {
        return new PackRat(this);
    }
}
