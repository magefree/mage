package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class KuldothaPhoenix extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "during your upkeep and only if you control three or more artifacts",
            IsStepCondition.getMyUpkeep(), MetalcraftCondition.instance
    );

    public KuldothaPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying, haste        
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // <i>Metalcraft</i> &mdash; {4}: Return Kuldotha Phoenix from your graveyard to the battlefield.
        // Activate this ability only during your upkeep and only if you control three or more artifacts.        
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                new ManaCostsImpl<>("{4}"), condition
        );
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        ability.addHint(MetalcraftHint.instance);
        this.addAbility(ability);
    }

    private KuldothaPhoenix(final KuldothaPhoenix card) {
        super(card);
    }

    @Override
    public KuldothaPhoenix copy() {
        return new KuldothaPhoenix(this);
    }
}
