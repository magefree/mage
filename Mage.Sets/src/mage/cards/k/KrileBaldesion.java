package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrileBaldesion extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value equal to that spell's mana value from your graveyard"
    );

    static {
        filter.add(KrileBaldesionPredicate.instance);
    }

    public KrileBaldesion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Trace Aether -- Whenever you cast a noncreature spell, you may return target creature card with mana value equal to that spell's mana value from your graveyard to your hand. Do this only once each turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, true
        ).setDoOnlyOnceEachTurn(true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.withFlavorWord("Trace Aether"));
    }

    private KrileBaldesion(final KrileBaldesion card) {
        super(card);
    }

    @Override
    public KrileBaldesion copy() {
        return new KrileBaldesion(this);
    }
}

enum KrileBaldesionPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return CardUtil
                .getEffectValueFromAbility(input.getSource(), "spellCast", Spell.class)
                .map(Spell::getManaValue)
                .filter(x -> x == input.getObject().getManaValue())
                .isPresent();
    }
}
