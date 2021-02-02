
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.keyword.FatesealEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MesmericSliver extends CardImpl {

    private static final FilterCreaturePermanent filterSliver = new FilterCreaturePermanent();

    static {
        filterSliver.add(SubType.SLIVER.getPredicate());
    }

    public MesmericSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "When this permanent enters the battlefield, you may fateseal 1."
        Ability ability = new EntersBattlefieldTriggeredAbility(new FatesealEffect(1), true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield,
                filterSliver, "All Slivers have \"When this permanent enters the battlefield, you may fateseal 1.\" <i>(To fateseal 1, its controller looks at the top card of an opponent's library, then they may put that card on the bottom of that library.)</i>")));
    }

    private MesmericSliver(final MesmericSliver card) {
        super(card);
    }

    @Override
    public MesmericSliver copy() {
        return new MesmericSliver(this);
    }
}
