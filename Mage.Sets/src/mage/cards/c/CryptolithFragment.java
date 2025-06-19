package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CryptolithFragment extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.EACH_PLAYER, ComparisonType.OR_LESS, 10);

    public CryptolithFragment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.secondSideCardClazz = mage.cards.a.AuroraOfEmrakul.class;

        // Cryptolith Fragment enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color. Each player loses 1 life.
        Ability anyColorManaAbility = new AnyColorManaAbility();
        anyColorManaAbility.addEffect(new LoseLifeAllPlayersEffect(1));
        this.addAbility(anyColorManaAbility);

        // At the beginning of your upkeep, if each player has 10 or less life, transform Cryptolith Fragment.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition));
    }

    private CryptolithFragment(final CryptolithFragment card) {
        super(card);
    }

    @Override
    public CryptolithFragment copy() {
        return new CryptolithFragment(this);
    }
}
