package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AniktheaHandOfErebos extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("enchantment creatures");
    private static final FilterCard filter2 = new FilterEnchantmentCard("non-Aura enchantment card from your graveyard");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter2.add(Predicates.not(SubType.AURA.getPredicate()));
    }

    public AniktheaHandOfErebos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Other enchantment creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Anikthea enters the battlefield or attacks, exile up to one target non-Aura enchantment card from your graveyard. Create a token that's a copy of that card, except it's a 3/3 black Zombie creature in addition to its other types.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AniktheaHandOfErebosEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
        this.addAbility(ability);
    }

    private AniktheaHandOfErebos(final AniktheaHandOfErebos card) {
        super(card);
    }

    @Override
    public AniktheaHandOfErebos copy() {
        return new AniktheaHandOfErebos(this);
    }
}

class AniktheaHandOfErebosEffect extends OneShotEffect {

    AniktheaHandOfErebosEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target non-Aura enchantment card from your graveyard. Create a token that's " +
                "a copy of that card, except it's a 3/3 black Zombie creature in addition to its other types";
    }

    private AniktheaHandOfErebosEffect(final AniktheaHandOfErebosEffect effect) {
        super(effect);
    }

    @Override
    public AniktheaHandOfErebosEffect copy() {
        return new AniktheaHandOfErebosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        new CreateTokenCopyTargetEffect()
                .setPermanentModifier((token) -> {
                    token.addCardType(CardType.CREATURE);
                    token.addSubType(SubType.ZOMBIE);
                    token.setColor(ObjectColor.BLACK);
                    token.setPower(3);
                    token.setToughness(3);
                })
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
        return true;
    }
}
