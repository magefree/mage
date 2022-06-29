
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class ObeliskOfAlara extends CardImpl {

    public ObeliskOfAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {1}{W}, {tap}: You gain 5 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(5), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {1}{U}, {tap}: Draw a card, then discard a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {1}{B}, {tap}: Target creature gets -2/-2 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // {1}{R}, {tap}: Obelisk of Alara deals 3 damage to target player.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
        // {1}{G}, {tap}: Target creature gets +4/+4 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ObeliskOfAlara(final ObeliskOfAlara card) {
        super(card);
    }

    @Override
    public ObeliskOfAlara copy() {
        return new ObeliskOfAlara(this);
    }
}
