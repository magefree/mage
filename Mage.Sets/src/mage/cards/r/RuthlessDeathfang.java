package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class RuthlessDeathfang extends CardImpl {

    public RuthlessDeathfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you sacrifice a creature, target opponent sacrifices a creature.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"),
                StaticFilters.FILTER_PERMANENT_CREATURE
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RuthlessDeathfang(final RuthlessDeathfang card) {
        super(card);
    }

    @Override
    public RuthlessDeathfang copy() {
        return new RuthlessDeathfang(this);
    }
}
