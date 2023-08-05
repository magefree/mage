package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SkitteringCicada extends CardImpl {

    public static final FilterCard filterCard = new FilterCard("colorless spells");
    public static final FilterSpell filterSpell = new FilterSpell("a colorless spell");

    static {
        filterCard.add(ColorlessPredicate.instance);
        filterSpell.add(ColorlessPredicate.instance);
    }

    public SkitteringCicada(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may cast colorless spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
            new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filterCard)
        ));

        // Whenever you cast a colorless spell, until end of turn, Skittering Cicada gains trample and gets +X/+X, where X is that spell's mana value.
        TriggeredAbility trigger = new SpellCastControllerTriggeredAbility(
            new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("until end of turn, {this} gains trample"),
            filterSpell, false, true
        );
        trigger.addEffect(new SkitteringCicadaBoostEffect());

        this.addAbility(trigger);
    }

    private SkitteringCicada(final SkitteringCicada card) {
        super(card);
    }

    @Override
    public SkitteringCicada copy() {
        return new SkitteringCicada(this);
    }
}

class SkitteringCicadaBoostEffect extends OneShotEffect {

    public SkitteringCicadaBoostEffect() {
        super(Outcome.BoostCreature);
        this.staticText = " and gets +X/+X, where X is that spell's mana value";
    }

    public SkitteringCicadaBoostEffect(final SkitteringCicadaBoostEffect effect) {
        super(effect);
    }

    @Override
    public SkitteringCicadaBoostEffect copy() {
        return new SkitteringCicadaBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getManaValue();
            if (cmc > 0) {
                game.addEffect(new BoostSourceEffect(cmc, cmc, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
