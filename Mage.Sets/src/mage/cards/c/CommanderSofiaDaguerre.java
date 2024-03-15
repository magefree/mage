package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.JunkToken;
import mage.target.TargetPermanent;

/**
 * @author Cguy7777
 */
public final class CommanderSofiaDaguerre extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("legendary permanent");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public CommanderSofiaDaguerre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Crash Landing -- When Commander Sofia Daguerre enters the battlefield,
        // destroy up to one target legendary permanent. That permanent's controller creates a Junk token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new CreateTokenControllerTargetPermanentEffect(new JunkToken())
                .setText("that permanent's controller creates a Junk token"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability.withFlavorWord("Crash Landing"));
    }

    private CommanderSofiaDaguerre(final CommanderSofiaDaguerre card) {
        super(card);
    }

    @Override
    public CommanderSofiaDaguerre copy() {
        return new CommanderSofiaDaguerre(this);
    }
}
