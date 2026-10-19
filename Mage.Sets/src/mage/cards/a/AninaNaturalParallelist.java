package mage.cards.a;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.UUID;

public final class AninaNaturalParallelist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with {X} in its mana cost");

    static {
        filter.add(AninaXSpellPredicate.instance);
    }

    public AninaNaturalParallelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell with {X} in its mana cost, conjure a random creature card with mana value X onto the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AninaNaturalParallelistEffect(), filter, false));
        // {T}: Add {G}{U}. Spend this mana only to cast spells with {X} in their mana costs.
        Ability manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new AninaConditionalMana(), new TapSourceCost());
        manaAbility.getEffects().get(0).setText("Add {G}{U}. Spend this mana only to cast spells with {X} in their mana costs");
        this.addAbility(manaAbility);
    }

    private AninaNaturalParallelist(final AninaNaturalParallelist card) {
        super(card);
    }

    @Override
    public AninaNaturalParallelist copy() {
        return new AninaNaturalParallelist(this);
    }
}

enum AninaXSpellPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof Spell && ((Spell) input).getCard().getManaCost().containsX();
    }
}

class AninaNaturalParallelistEffect extends OneShotEffect {

    AninaNaturalParallelistEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "conjure a random creature card with mana value X onto the battlefield";
    }

    private AninaNaturalParallelistEffect(final AninaNaturalParallelistEffect effect) {
        super(effect);
    }

    @Override
    public AninaNaturalParallelistEffect copy() {
        return new AninaNaturalParallelistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && ConjureRandomCreatureEffect.conjure(
                CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0), Zone.BATTLEFIELD, game, source
        ) != null;
    }
}

class AninaConditionalMana extends ConditionalMana {

    AninaConditionalMana() {
        super(Mana.GreenMana(1));
        add(Mana.BlueMana(1));
        staticText = "Spend this mana only to cast spells with {X} in their mana costs";
        addCondition(new AninaManaCondition());
    }

    private AninaConditionalMana(final AninaConditionalMana mana) {
        super(mana);
    }

    @Override
    public AninaConditionalMana copy() {
        return new AninaConditionalMana(this);
    }
}

class AninaManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        MageObject object = game.getObject(source);
        return source instanceof SpellAbility && object != null && object.getManaCost().containsX();
    }
}
