package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrzhovEuthanist extends CardImpl {

    public OrzhovEuthanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haunt
        // When Orzhov Euthanist enters the battlefield or the creature it haunts dies, destroy target creature that was dealt damage this turn.
        Ability ability = new HauntAbility(this, new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_DAMAGED_THIS_TURN));
        this.addAbility(ability);
    }

    private OrzhovEuthanist(final OrzhovEuthanist card) {
        super(card);
    }

    @Override
    public OrzhovEuthanist copy() {
        return new OrzhovEuthanist(this);
    }
}
