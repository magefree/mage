package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrondTheDawnClad extends CardImpl {

    private static final Condition condition = new EnchantedSourceCondition();

    public KrondTheDawnClad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying, vigilance
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Krond the Dawn-Clad attacks, if it's enchanted, exile target permanent.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect(), false)
                .withInterveningIf(condition).withRuleTextReplacement(true);
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
