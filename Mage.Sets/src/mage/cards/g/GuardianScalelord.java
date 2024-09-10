package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianScalelord extends CardImpl {

    private static final FilterCard filter
            = new FilterNonlandCard("nonland permanent card with mana value less than this creature's power");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(GuardianScalelordPredicate.instance);
    }

    public GuardianScalelord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Flying
        backupAbility.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, return target nonland permanent card with mana value X or less from your graveyard to the battlefield, where X is this creature's power.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target nonland permanent card with mana value X or less " +
                        "from your graveyard to the battlefield, where X is this creature's power"))
                .setTriggerPhrase("Whenever this creature attacks, ");
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        backupAbility.addAbility(ability);
    }

    private GuardianScalelord(final GuardianScalelord card) {
        super(card);
    }

    @Override
    public GuardianScalelord copy() {
        return new GuardianScalelord(this);
    }
}

enum GuardianScalelordPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(p -> input.getObject().getManaValue() <= p)
                .orElse(false);
    }
}
