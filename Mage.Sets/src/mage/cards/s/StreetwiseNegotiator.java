package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreetwiseNegotiator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(StreetwiseNegotiatorPredicate.instance);
    }

    public StreetwiseNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // This creature assigns combat damage equal to its toughness rather than its power.
        backupAbility.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessEffect(filter, false)
                .setText("this creature assigns combat damage equal to its toughness rather than its power")));
    }

    private StreetwiseNegotiator(final StreetwiseNegotiator card) {
        super(card);
    }

    @Override
    public StreetwiseNegotiator copy() {
        return new StreetwiseNegotiator(this);
    }
}

enum StreetwiseNegotiatorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return !AnotherPredicate.instance.apply(input, game);
    }
}