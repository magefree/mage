
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class ErsatzGnomes extends CardImpl {

    public ErsatzGnomes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target spell becomes colorless.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(ObjectColor.COLORLESS, Duration.Custom), new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

        // {T}: Target permanent becomes colorless until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(ObjectColor.COLORLESS, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private ErsatzGnomes(final ErsatzGnomes card) {
        super(card);
    }

    @Override
    public ErsatzGnomes copy() {
        return new ErsatzGnomes(this);
    }
}
