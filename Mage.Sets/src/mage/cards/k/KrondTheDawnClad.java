
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class KrondTheDawnClad extends CardImpl {

    public KrondTheDawnClad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying, vigilance
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Krond the Dawn-Clad attacks, if it's enchanted, exile target permanent.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new ExileTargetEffect(), false),
                new EnchantedSourceCondition(),
                "Whenever {this} attacks, if it's enchanted, exile target permanent.");
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private KrondTheDawnClad(final KrondTheDawnClad card) {
        super(card);
    }

    @Override
    public KrondTheDawnClad copy() {
        return new KrondTheDawnClad(this);
    }
}
