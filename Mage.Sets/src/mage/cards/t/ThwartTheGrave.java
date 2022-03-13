package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThwartTheGrave extends CardImpl {

    public ThwartTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance).setRuleAtTheTop(true));

        // Return target creature card and up to one target Cleric, Rogue, Warrior, or Wizard creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("Return target creature card and up to one target Cleric, Rogue, Warrior, or Wizard creature card from your graveyard to the battlefield."));
        this.getSpellAbility().addTarget(new ThwartTheGraveTarget());
    }

    private ThwartTheGrave(final ThwartTheGrave card) {
        super(card);
    }

    @Override
    public ThwartTheGrave copy() {
        return new ThwartTheGrave(this);
    }
}

class ThwartTheGraveTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card and up to one target Cleric, Rogue, Warrior, or Wizard creature card from your graveyard"
    );

    ThwartTheGraveTarget() {
        super(1, 2, filter, false);
    }

    private ThwartTheGraveTarget(final ThwartTheGraveTarget target) {
        super(target);
    }

    @Override
    public ThwartTheGraveTarget copy() {
        return new ThwartTheGraveTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        if (!this.getTargets().isEmpty()) {
            possibleTargets.removeIf(uuid -> {
                Card card = game.getCard(uuid);
                return card != null
                        && !card.hasSubtype(SubType.CLERIC, game)
                        && !card.hasSubtype(SubType.ROGUE, game)
                        && !card.hasSubtype(SubType.WARRIOR, game)
                        && !card.hasSubtype(SubType.WIZARD, game);
            });
        }
        return possibleTargets;
    }
}
