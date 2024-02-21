package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ImpToken;
import mage.game.stack.Spell;

/**
 *
 * @author DominionSpy
 */
public final class JudithCarnageConnoisseur extends CardImpl {

    public JudithCarnageConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, choose one --
        // * That spell gains deathtouch and lifelink.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new JudithCarnageConnoisseurEffect(),
                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY,
                false, SetTargetPointer.SPELL);

        // * Create a 2/2 red Imp creature token with "When this creature dies, it deals 2 damage to each opponent."
        Mode mode = new Mode(new CreateTokenEffect(new ImpToken()));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private JudithCarnageConnoisseur(final JudithCarnageConnoisseur card) {
        super(card);
    }

    @Override
    public JudithCarnageConnoisseur copy() {
        return new JudithCarnageConnoisseur(this);
    }
}

class JudithCarnageConnoisseurEffect extends ContinuousEffectImpl {

    JudithCarnageConnoisseurEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "That spell gains deathtouch and lifelink";
    }

    private JudithCarnageConnoisseurEffect(final JudithCarnageConnoisseurEffect effect) {
        super(effect);
    }

    @Override
    public JudithCarnageConnoisseurEffect copy() {
        return new JudithCarnageConnoisseurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }

        Card card = spell.getCard();
        game.getState().addOtherAbility(card, DeathtouchAbility.getInstance());
        game.getState().addOtherAbility(card, LifelinkAbility.getInstance());
        return true;
    }
}
