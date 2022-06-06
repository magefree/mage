
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfTheMists extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.KNIGHT, "Knight");

    public KnightOfTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // When Knight of the Mists enters the battlefield, you may pay {U}. If you don't, destroy target Knight and it can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new InfoEffect(""), new DestroyTargetEffect(true), new ManaCostsImpl<>("{U}")
        ).setText("you may pay {U}. If you don't, destroy target Knight and it can't be regenerated."));
        ability.addTarget(new TargetPermanent(filter));
        addAbility(ability);
    }

    private KnightOfTheMists(final KnightOfTheMists card) {
        super(card);
    }

    @Override
    public KnightOfTheMists copy() {
        return new KnightOfTheMists(this);
    }
}
