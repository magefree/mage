package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.JunkToken;
import mage.watchers.common.PlayerAttackedWatcher;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class RoseCutthroatRaider extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Junk");

    static {
        filter.add(SubType.JUNK.getPredicate());
    }

    public RoseCutthroatRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Raid -- At end of combat on your turn, if you attacked this turn, create a Junk token for each opponent you attacked.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EndOfCombatTriggeredAbility(
                new CreateTokenEffect(new JunkToken(), AttackedThisTurnOpponentsCount.instance), false), RaidCondition.instance,
                "At end of combat on your turn, if you attacked this turn, create a Junk token for each opponent you attacked.");
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(AttackedThisTurnOpponentsCount.getHint());
        ability.addWatcher(new PlayerAttackedWatcher());
        ability.addWatcher(new PlayersAttackedThisTurnWatcher());
        this.addAbility(ability);

        // Whenever you sacrifice a Junk, add {R}.
        this.addAbility(new SacrificePermanentTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)), filter));
    }

    private RoseCutthroatRaider(final RoseCutthroatRaider card) {
        super(card);
    }

    @Override
    public RoseCutthroatRaider copy() {
        return new RoseCutthroatRaider(this);
    }
}
