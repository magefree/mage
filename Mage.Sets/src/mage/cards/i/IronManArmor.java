package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class IronManArmor extends CardImpl {

    public IronManArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+1 and has flying.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        this.addAbility(ability);

        // {2}: If this Equipment isn’t a creature, it becomes a 0/0 Construct Hero artifact creature with flying
        // and “This creature gets +1/+1 for each artifact you control” until end of turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(0, 0)
                                .withType(CardType.ARTIFACT)
                                .withSubType(SubType.CONSTRUCT)
                                .withSubType(SubType.HERO)
                                .withAbility(FlyingAbility.getInstance())
                                .withAbility(new SimpleStaticAbility(
                                        new BoostSourceEffect(ArtifactYouControlCount.instance, ArtifactYouControlCount.instance, Duration.WhileOnBattlefield)
                                                .setText("This creature gets +1/+1 for each artifact you control")
                                )),
                        CardType.ARTIFACT,
                        Duration.EndOfTurn
                ).setText("If this Equipment isn't a creature, it becomes a 0/0 Construct Hero artifact creature with flying and \"This creature gets +1/+1 for each artifact you control\" until end of turn."),
                new GenericManaCost(2),
                new InvertCondition(IsCreatureCondition.instance)
        //Conditiontext already in the BecomesCreatureSourceEffect
        ).hideCondition());

        // Retains Equip ability
        // Fails to equip if activated as creature according to 301.5c 701.3b 701.3a

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private IronManArmor(final IronManArmor card) {
        super(card);
    }

    @Override
    public IronManArmor copy() {
        return new IronManArmor(this);
    }
}

enum IsCreatureCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getSourceObject(game).isCreature(game);
    }

    @Override
    public String toString() {
        return "this is a creature";
    }
}
