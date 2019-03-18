
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author cbt33
 */
public final class TestamentOfFaith extends CardImpl {

    public TestamentOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");


        // {X}: Testament of Faith becomes an X/X Wall creature with defender in addition to its other types until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TestamentOfFaithBecomesCreatureSourceEffect(new TestamentOfFaithToken(), "enchantment", Duration.EndOfTurn), new VariableManaCost()));
    }

    public TestamentOfFaith(final TestamentOfFaith card) {
        super(card);
    }

    @Override
    public TestamentOfFaith copy() {
        return new TestamentOfFaith(this);
    }
}

class TestamentOfFaithBecomesCreatureSourceEffect extends ContinuousEffectImpl implements SourceEffect {

    protected Token token;
    protected String type;
    protected int zoneChangeCounter;

    public TestamentOfFaithBecomesCreatureSourceEffect(Token token, String type, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.type = type;
        setText();
    }

    public TestamentOfFaithBecomesCreatureSourceEffect(final TestamentOfFaithBecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
    }

    @Override
    public TestamentOfFaithBecomesCreatureSourceEffect copy() {
        return new TestamentOfFaithBecomesCreatureSourceEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.getAffectedObjects().add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!token.getCardType().isEmpty()) {
                            for (CardType t : token.getCardType()) {
                                if (!permanent.getCardType().contains(t)) {
                                    permanent.addCardType(t);
                                }
                            }
                        }
                        if (type != null && type.isEmpty() || type == null) {
                            permanent.getSubtype(game).clear();
                        }
                        if (!token.getSubtype(game).isEmpty()) {
                            permanent.getSubtype(game).addAll(token.getSubtype(game));
                        }
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        if (token.getColor(game).hasColor()) {
                            permanent.getColor(game).setColor(token.getColor(game));
                        }
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        if (!token.getAbilities().isEmpty()) {
                            for (Ability ability: token.getAbilities()) {
                                permanent.addAbility(ability, source.getSourceId(), game, false);
                            }
                        }
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        MageInt power = new MageInt(source.getManaCosts().getVariableCosts().get(0).getAmount());
                        MageInt toughness = new MageInt(source.getManaCosts().getVariableCosts().get(0).getAmount());
                        permanent.getPower().setValue(power.getValue());
                        permanent.getToughness().setValue(toughness.getValue());
                    }
            }
            return true;
        } else {
            if (duration == Duration.Custom) {
                this.discard();
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        staticText = "{this} becomes an X/X Wall creature with defender in addition to its other types until end of turn";
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}


class TestamentOfFaithToken extends TokenImpl {
    TestamentOfFaithToken() {
        super("Wall", "X/X Wall creature with defender");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.WALL);
        color.setWhite(true);
        this.addAbility(DefenderAbility.getInstance());
    }
    public TestamentOfFaithToken(final TestamentOfFaithToken token) {
        super(token);
    }

    public TestamentOfFaithToken copy() {
        return new TestamentOfFaithToken(this);
    }
}