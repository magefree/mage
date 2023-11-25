package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzureFleetAdmiral extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures the monarch controls");

    static {
        filter.add(AzureFleetAdmiralPredicate.instance);
    }

    public AzureFleetAdmiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Azure Fleet Admiral enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // Azure Fleet Admiral can't be blocked by creatures the monarch controls.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByAllSourceEffect(filter, Duration.WhileOnBattlefield)
        ));
    }

    private AzureFleetAdmiral(final AzureFleetAdmiral card) {
        super(card);
    }

    @Override
    public AzureFleetAdmiral copy() {
        return new AzureFleetAdmiral(this);
    }
}

enum AzureFleetAdmiralPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isControlledBy(game.getMonarchId());
    }
}
