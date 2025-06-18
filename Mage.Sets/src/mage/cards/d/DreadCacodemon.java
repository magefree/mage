package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DreadCacodemon extends CardImpl {

    public DreadCacodemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Dread Cacodemon enters the battlefield, 
        // if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control. 
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DestroyAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)
        ).withInterveningIf(CastFromHandSourcePermanentCondition.instance);
        ability.addEffect(new TapAllEffect(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES).concatBy(", then"));
        this.addAbility(ability, new CastFromHandWatcher());
    }

    private DreadCacodemon(final DreadCacodemon card) {
        super(card);
    }

    @Override
    public DreadCacodemon copy() {
        return new DreadCacodemon(this);
    }
}
