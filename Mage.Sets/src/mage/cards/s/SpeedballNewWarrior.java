package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class SpeedballNewWarrior extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets {this}");

    static {
        filter.add(SpeedballNewWarriorPredicate.instance);
    }

    public SpeedballNewWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player casts a spell that targets Speedball, he gets +2/+2 until end of turn. You may choose new targets for that spell.
        Ability ability = new SpellCastAllTriggeredAbility(
            new BoostSourceEffect(2, 2, Duration.EndOfTurn, "he"),
            filter, false, SetTargetPointer.SPELL
        );
        ability.addEffect(new ChooseNewTargetsTargetEffect()
            .setText("you may choose new targets for that spell"));
        this.addAbility(ability);
    }

    private SpeedballNewWarrior(final SpeedballNewWarrior card) {
        super(card);
    }

    @Override
    public SpeedballNewWarrior copy() {
        return new SpeedballNewWarrior(this);
    }
}

enum SpeedballNewWarriorPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Permanent source = input.getSource().getSourcePermanentIfItStillExists(game);
        if (source == null || input.getObject() == null) {
            return false;
        }
        for (SpellAbility spellAbility : input.getObject().getSpellAbilities()) {
            if (CardUtil.getAllSelectedTargets(spellAbility, game).contains(input.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}
