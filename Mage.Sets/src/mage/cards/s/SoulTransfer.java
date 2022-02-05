package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class SoulTransfer extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card from your graveyard");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    public SoulTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose one. If you control an artifact and an enchantment as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control an artifact and an enchantment as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(SoulTransferCondition.instance);

        // • Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Return target creature or planeswalker card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode);
    }

    private SoulTransfer(final SoulTransfer card) {
        super(card);
    }

    @Override
    public SoulTransfer copy() {
        return new SoulTransfer(this);
    }
}

enum SoulTransferCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        boolean artifact = false;
        boolean enchantment = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isArtifact(game)) {
                artifact = true;
            }
            if (permanent.isEnchantment(game)) {
                enchantment = true;
            }
            if (artifact && enchantment) {
                return true;
            }
        }
        return false;
    }
}
