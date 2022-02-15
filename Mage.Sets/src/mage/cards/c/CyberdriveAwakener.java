package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CyberdriveAwakener extends CardImpl {

    public CyberdriveAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other artifact creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // When Cyberdrive Awakener enters the battlefield, until end of turn, each noncreature artifact you control becomes an artifact creature with base power and toughness 4/4.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CyberdriveAwakenerEffect()));
    }

    private CyberdriveAwakener(final CyberdriveAwakener card) {
        super(card);
    }

    @Override
    public CyberdriveAwakener copy() {
        return new CyberdriveAwakener(this);
    }
}

class CyberdriveAwakenerEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    CyberdriveAwakenerEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "until end of turn, each noncreature artifact you control " +
                "becomes an artifact creature with base power and toughness 4/4";
    }

    private CyberdriveAwakenerEffect(final CyberdriveAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(permanent -> new MageObjectReference(permanent, game))
                .forEach(affectedObjectList::add);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<Permanent> permanents = affectedObjectList
                .stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            discard();
            return false;
        }
        for (Permanent permanent : permanents) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(4);
                        permanent.getToughness().setValue(4);
                    }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.PTChangingEffects_7;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public CyberdriveAwakenerEffect copy() {
        return new CyberdriveAwakenerEffect(this);
    }
}
