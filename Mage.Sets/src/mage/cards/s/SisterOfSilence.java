package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SisterOfSilence extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("instant spell, sorcery spell, activated ability, or triggered ability");

    static {
        filter.add(SisterOfSilencePredicate.instance);
    }

    public SisterOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Psychic Abomination -- When Sister of Silence enters the battlefield, counter target instant spell, sorcery spell, activated ability, or triggered ability.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability.withFlavorWord("Psychic Abomination"));
    }

    private SisterOfSilence(final SisterOfSilence card) {
        super(card);
    }

    @Override
    public SisterOfSilence copy() {
        return new SisterOfSilence(this);
    }
}

enum SisterOfSilencePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return !(input instanceof Spell) || input.isInstantOrSorcery(game);
    }
}