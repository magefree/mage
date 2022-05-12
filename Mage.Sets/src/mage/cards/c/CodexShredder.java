package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CodexShredder extends CardImpl {

    public CodexShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}: Target player puts the top card of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(
                new PutLibraryIntoGraveTargetEffect(1), new TapSourceCost()
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.
        ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private CodexShredder(final CodexShredder card) {
        super(card);
    }

    @Override
    public CodexShredder copy() {
        return new CodexShredder(this);
    }
}
