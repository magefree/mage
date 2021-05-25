package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class GeistOfTheLonelyVigil extends CardImpl {

    public GeistOfTheLonelyVigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Delirium</i> &mdash; Geist of the Lonely Vigil can attack as though it didn't have defender as long as there are four or more card types among cards in your graveyard.
        Effect effect = new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                DeliriumCondition.instance);
        effect.setText("<i>Delirium</i> &mdash; {this} can attack as though it didn't have defender as long as there are four or more card types among cards in your graveyard");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CardTypesInGraveyardHint.YOU));
    }

    private GeistOfTheLonelyVigil(final GeistOfTheLonelyVigil card) {
        super(card);
    }

    @Override
    public GeistOfTheLonelyVigil copy() {
        return new GeistOfTheLonelyVigil(this);
    }
}
