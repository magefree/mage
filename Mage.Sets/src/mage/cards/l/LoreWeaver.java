
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class LoreWeaver extends CardImpl {

    public LoreWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Ley Weaver (When this creature enters the battlefield, target player may put Ley Weaver into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Ley Weaver"));

        // {5}{U}{U}: Target player draws two cards.
        Ability ability = new SimpleActivatedAbility(new DrawCardTargetEffect(2), new ManaCostsImpl<>("{5}{U}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LoreWeaver(final LoreWeaver card) {
        super(card);
    }

    @Override
    public LoreWeaver copy() {
        return new LoreWeaver(this);
    }
}
