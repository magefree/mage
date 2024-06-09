package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergentWoodwurm extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card with mana value X or less");

    static {
        filter.add(EmergentWoodwurmPredicate.instance);
    }

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public EmergentWoodwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Backup 3
        BackupAbility backupAbility = new BackupAbility(this, 3);
        this.addAbility(backupAbility);

        // Whenever this creature attacks, look at the top X cards of your library, where X is its power. You may put a permanent card with mana value X or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        backupAbility.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                xValue, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        )).setTriggerPhrase("Whenever this creature attacks, "));
    }

    private EmergentWoodwurm(final EmergentWoodwurm card) {
        super(card);
    }

    @Override
    public EmergentWoodwurm copy() {
        return new EmergentWoodwurm(this);
    }
}

enum EmergentWoodwurmPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .of(input)
                .map(ObjectSourcePlayer::getSource)
                .map(ability -> ability.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(i -> input.getObject().getManaValue() <= i)
                .orElse(false);
    }
}