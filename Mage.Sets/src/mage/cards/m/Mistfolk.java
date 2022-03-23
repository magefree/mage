package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mistfolk extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that this creature");

    static {
        filter.add(MistfolkPredicate.instance);
    }

    public Mistfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}: Counter target spell that targets Mistfolk.
        Ability ability = new SimpleActivatedAbility(
                new CounterTargetEffect()
                        .setText("counter target spell that targets {this}"),
                new ManaCostsImpl("{U}")
        );
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private Mistfolk(final Mistfolk card) {
        super(card);
    }

    @Override
    public Mistfolk copy() {
        return new Mistfolk(this);
    }
}

enum MistfolkPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Permanent sourceObject = input.getSource().getSourcePermanentIfItStillExists(game);
        if (sourceObject == null || input.getObject() == null) {
            return false;
        }
        for (SpellAbility spellAbility : input.getObject().getSpellAbilities()) {
            for (Mode mode : spellAbility.getModes().values()) {
                for (Target target : spellAbility.getTargets()) {
                    if (target.getTargets().contains(input.getSourceId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}