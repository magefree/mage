
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SydriGalvanicGenius extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact creature");
    private static final FilterArtifactPermanent filterNonCreature = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SydriGalvanicGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SydriGalvanicGeniusEffect(), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetPermanent(filterNonCreature));
        this.addAbility(ability);

        // {W}{B}: Target artifact creature gains deathtouch and lifelink until end of turn.
        Effect effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, "Target artifact creature gains deathtouch");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{W}{B}"));
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, "and lifelink until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SydriGalvanicGenius(final SydriGalvanicGenius card) {
        super(card);
    }

    @Override
    public SydriGalvanicGenius copy() {
        return new SydriGalvanicGenius(this);
    }
}

class SydriGalvanicGeniusEffect extends ContinuousEffectImpl {

    public SydriGalvanicGeniusEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Target noncreature artifact becomes an artifact creature with power and toughness each equal to its mana value until end of turn";
    }

    public SydriGalvanicGeniusEffect(final SydriGalvanicGeniusEffect effect) {
        super(effect);
    }

    @Override
    public SydriGalvanicGeniusEffect copy() {
        return new SydriGalvanicGeniusEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent artifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!artifact.isArtifact(game)) {
                        artifact.addCardType(game, CardType.ARTIFACT);
                    }
                    if (!artifact.isCreature(game)) {
                        artifact.addCardType(game, CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = artifact.getManaValue();
                    artifact.getPower().setModifiedBaseValue(cmc);
                    artifact.getToughness().setModifiedBaseValue(cmc);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
