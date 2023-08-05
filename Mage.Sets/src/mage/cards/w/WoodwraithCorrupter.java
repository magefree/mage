
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class WoodwraithCorrupter extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public WoodwraithCorrupter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // {1}{B}{G}, {T}: Target Forest becomes a 4/4 black and green Elemental Horror creature. It's still a land.
        Effect effect = new BecomesCreatureTargetEffect(new WoodwraithCorrupterToken(), false, true, Duration.Custom);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WoodwraithCorrupter(final WoodwraithCorrupter card) {
        super(card);
    }

    @Override
    public WoodwraithCorrupter copy() {
        return new WoodwraithCorrupter(this);
    }
}

class WoodwraithCorrupterToken extends TokenImpl {

    public WoodwraithCorrupterToken() {
        super("", "4/4 black and green Elemental Horror creature");
        cardType.add(CardType.CREATURE);

        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        subtype.add(SubType.HORROR);

        power = new MageInt(4);
        toughness = new MageInt(4);
    }
    public WoodwraithCorrupterToken(final WoodwraithCorrupterToken token) {
        super(token);
    }

    public WoodwraithCorrupterToken copy() {
        return new WoodwraithCorrupterToken(this);
    }
}
