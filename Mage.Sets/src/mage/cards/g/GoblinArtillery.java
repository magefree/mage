package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GoblinArtillery extends CardImpl {

    public GoblinArtillery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: This creature deals 2 damage to any target and 3 damage to you.
        Ability ability = new SimpleActivatedAbility(new DamageTargetAndYouEffect(2, 3), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinArtillery(final GoblinArtillery card) {
        super(card);
    }

    @Override
    public GoblinArtillery copy() {
        return new GoblinArtillery(this);
    }
}
