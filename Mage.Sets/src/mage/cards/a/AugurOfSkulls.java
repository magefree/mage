package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AugurOfSkulls extends CardImpl {

    public AugurOfSkulls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}: Regenerate Augur of Skulls.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));

        // Sacrifice Augur of Skulls: Target player discards two cards. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DiscardTargetEffect(2), new SacrificeSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AugurOfSkulls(final AugurOfSkulls card) {
        super(card);
    }

    @Override
    public AugurOfSkulls copy() {
        return new AugurOfSkulls(this);
    }
}
