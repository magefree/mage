package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CryptolithFragment extends TransformingDoubleFacedCard {

    private static final Condition condition = new LifeCompareCondition(TargetController.EACH_PLAYER, ComparisonType.OR_LESS, 10);

    public CryptolithFragment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Aurora of Emrakul",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.REFLECTION}, "");

        // Cryptolith Fragment
        // Cryptolith Fragment enters the battlefield tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color. Each player loses 1 life.
        Ability anyColorManaAbility = new AnyColorManaAbility();
        anyColorManaAbility.addEffect(new LoseLifeAllPlayersEffect(1));
        this.getLeftHalfCard().addAbility(anyColorManaAbility);

        // At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition));

        // Aurora of Emrakul
        this.getRightHalfCard().setPT(1, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Aurora of Emrakul attacks, each opponent loses 3 life.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(3), false));
    }

    private CryptolithFragment(final CryptolithFragment card) {
        super(card);
    }

    @Override
    public CryptolithFragment copy() {
        return new CryptolithFragment(this);
    }
}
