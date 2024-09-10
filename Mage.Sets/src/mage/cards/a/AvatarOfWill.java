package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentHasNoCardsInHandCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AvatarOfWill extends CardImpl {

    public AvatarOfWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // If an opponent has no cards in hand, Avatar of Will costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(6, OpponentHasNoCardsInHandCondition.instance)
                        .setText("If an opponent has no cards in hand, this spell costs {6} less to cast")
                ).addHint(new ConditionHint(OpponentHasNoCardsInHandCondition.instance, "Opponent has no cards in hand"))
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private AvatarOfWill(final AvatarOfWill card) {
        super(card);
    }

    @Override
    public AvatarOfWill copy() {
        return new AvatarOfWill(this);
    }
}
