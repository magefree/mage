
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class EsperBattlemage extends CardImpl {

    public EsperBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // {W}, {tap}: Prevent the next 2 damage that would be dealt to you this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToControllerEffect(Duration.EndOfTurn, 2),
                new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {B}, {tap}: Target creature gets -1/-1 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private EsperBattlemage(final EsperBattlemage card) {
        super(card);
    }

    @Override
    public EsperBattlemage copy() {
        return new EsperBattlemage(this);
    }
}
