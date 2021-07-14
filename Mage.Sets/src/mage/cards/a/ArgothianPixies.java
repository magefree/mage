package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class ArgothianPixies extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArgothianPixies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Argothian Pixies can't be blocked by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Prevent all damage that would be dealt to Argothian Pixies by artifact creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ArgothianPixiesPreventDamageFromArtifactsEffect(Duration.WhileOnBattlefield)));

    }

    private ArgothianPixies(final ArgothianPixies card) {
        super(card);
    }

    @Override
    public ArgothianPixies copy() {
        return new ArgothianPixies(this);
    }
}

class ArgothianPixiesPreventDamageFromArtifactsEffect extends PreventionEffectImpl {

    public ArgothianPixiesPreventDamageFromArtifactsEffect(Duration duration) {
        super(duration);
        staticText = "Prevent all damage that would be dealt to {this} by artifact creatures";
    }

    public ArgothianPixiesPreventDamageFromArtifactsEffect(final ArgothianPixiesPreventDamageFromArtifactsEffect effect) {
        super(effect);
    }

    @Override
    public ArgothianPixiesPreventDamageFromArtifactsEffect copy() {
        return new ArgothianPixiesPreventDamageFromArtifactsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.getCardType(game).contains(CardType.ARTIFACT)) {
                return (event.getTargetId().equals(source.getSourceId()));
            }
        }
        return false;
    }
}
