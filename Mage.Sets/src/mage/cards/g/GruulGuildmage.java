
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author Loki
 */
public final class GruulGuildmage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public GruulGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/G}{R/G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{R}, Sacrifice a land: Gruul Guildmage deals 2 damage to target player.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{3}{R}"));
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        firstAbility.addTarget(new TargetPlayerOrPlaneswalker(1));
        this.addAbility(firstAbility);
        // {3}{G}: Target creature gets +2/+2 until end of turn.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}"));
        secondAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(secondAbility);
    }

    private GruulGuildmage(final GruulGuildmage card) {
        super(card);
    }

    @Override
    public GruulGuildmage copy() {
        return new GruulGuildmage(this);
    }
}
