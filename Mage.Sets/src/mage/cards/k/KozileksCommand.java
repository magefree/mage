package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ScryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KozileksCommand extends CardImpl {

    public KozileksCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.INSTANT}, "{X}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Target player creates X 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addEffect(new CreateTokenTargetEffect(new EldraziSpawnToken(), ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer().withChooseHint("create tokens"));

        // * Target player scries X, then draws a card.
        Mode mode = new Mode(new ScryTargetEffect(ManacostVariableValue.REGULAR));
        mode.addEffect(new DrawCardTargetEffect(1).setText(", then draws a card"));
        mode.addTarget(new TargetPlayer().withChooseHint("scries then draw"));
        this.getSpellAbility().addMode(mode);

        // * Exile target creature with mana value X or less.
        mode = new Mode(new ExileTargetEffect()
                .setText("exile target creature with mana value X or less"));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // * Exile up to X target cards from graveyards.
        mode = new Mode(new ExileTargetEffect()
                .setText("exile up to X target cards from graveyards"));
        mode.addTarget(new TargetCardInGraveyard(0, 0, new FilterCard("cards from graveyards")));
        this.getSpellAbility().addMode(mode);

        this.getSpellAbility().setTargetAdjuster(KozileksCommandAdjuster.instance);
    }

    private KozileksCommand(final KozileksCommand card) {
        super(card);
    }

    @Override
    public KozileksCommand copy() {
        return new KozileksCommand(this);
    }
}

enum KozileksCommandAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        // adjust targets is called for every selected mode
        Mode mode = ability.getModes().getMode();
        int xValue = ability.getManaCostsToPay().getX();
        for (Effect effect : mode.getEffects()) {
            if (effect instanceof ExileTargetEffect) {
                Target target = mode.getTargets().get(0);
                if (target instanceof TargetCreaturePermanent) {
                    mode.getTargets().clear();
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue + " or less");
                    filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, xValue));
                    mode.addTarget(new TargetCreaturePermanent(filter));
                }
                if (target instanceof TargetCardInGraveyard) {
                    mode.getTargets().clear();
                    mode.addTarget(new TargetCardInGraveyard(0, xValue, new FilterCard("cards from graveyards")));
                }
            }
        }
    }
}