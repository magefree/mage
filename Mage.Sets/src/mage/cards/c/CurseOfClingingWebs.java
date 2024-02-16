package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiderToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfClingingWebs extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a nontoken creature enchanted player controls");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.ENCHANTED.getControllerPredicate());
    }

    public CurseOfClingingWebs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever a nontoken creature enchanted player controls dies, exile it and you create a 1/2 green Spider creature token with reach.
        Ability ability = new DiesCreatureTriggeredAbility(
                new ExileTargetEffect().setText("exile it"),
                false, filter, true
        );
        ability.addEffect(new CreateTokenEffect(new SpiderToken()).concatBy("and you"));
        this.addAbility(ability);
    }

    private CurseOfClingingWebs(final CurseOfClingingWebs card) {
        super(card);
    }

    @Override
    public CurseOfClingingWebs copy() {
        return new CurseOfClingingWebs(this);
    }
}
