package mage.cards.q;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class QuirionDruid extends CardImpl {

    public QuirionDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {G}, {T}: Target land becomes a 2/2 green creature that’s still a land. <i>(This effect lasts indefinitely.)</i>
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new QuirionDruidToken(), false, true, Duration.Custom), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private QuirionDruid(final QuirionDruid card) {
        super(card);
    }

    @Override
    public QuirionDruid copy() {
        return new QuirionDruid(this);
    }
}

class QuirionDruidToken extends TokenImpl {

    public QuirionDruidToken() {
        super("", "2/2 green creature");
        this.color.addColor(ObjectColor.GREEN);
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    public QuirionDruidToken(final QuirionDruidToken token) {
        super(token);
    }

    public QuirionDruidToken copy() {
        return new QuirionDruidToken(this);
    }
}
